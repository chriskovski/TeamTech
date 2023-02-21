from flask import Flask, request, jsonify
from flask_restful import Api, Resource
import os
import psycopg2
import base64

app = Flask(__name__)
api = Api(app)

class Booking(Resource):
    def post(self):
        # Extract data from the JSON payload
        json_payload = request.get_json()
        title = json_payload['title']
        name = json_payload['name']
        numberOfTickets = json_payload['numberOfTickets']

        # Insert the data into the database
        try:
            db_host = os.environ['DB_HOST']
            db_port = os.environ['DB_PORT']
            db_name = os.environ['DB_NAME']
            db_username_secret_name = 'postgres-secret'
            db_username_secret_namespace = 'my-namespace'
            db_username_key = 'username'
            db_password_key = 'password'

            config = Config()
            secret = config.read_namespaced_secret(name=db_username_secret_name, namespace=db_username_secret_namespace)

            username = base64.b64decode(secret.data[db_username_key]).decode('utf-8')
            password = base64.b64decode(secret.data[db_password_key]).decode('utf-8')

            conn = psycopg2.connect(
                host=db_host,
                port=db_port,
                database=db_name,
                user=username,
                password=password
            )

            cursor = conn.cursor()
            cursor.execute("INSERT INTO bookings (title, name, number_of_tickets) VALUES (%s, %s, %s)", (title, name, numberOfTickets))
            conn.commit()
            cursor.close()
            conn.close()
        except Exception as e:
            print(e)
            return {'message': 'Error: database error'}, 500

        # Return a response
        return {'message': 'Booking created successfully!'}, 201

class Alive(Resource):
    def get(self):
        return 'alive'

api.add_resource(Booking, '/booking')
api.add_resource(Alive, '/alive')

if __name__ == '__main__':
    app.run(debug=True)

