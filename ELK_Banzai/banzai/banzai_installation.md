# Installation guide

1. Insall banzai from helm chart:

```sh
helm repo add banzaicloud-stable https://kubernetes-charts.banzaicloud.com
helm repo update
helm upgrade --install --wait --create-namespace --namespace logging logging-operator banzaicloud-stable/logging-operator
```

2. Validate deployment:

```sh
kubectl -n logging get pods
kubectl get crd
```

3. Install elasticsearch operator:

```sh
kubectl create -f https://download.elastic.co/downloads/eck/2.6.1/crds.yaml # custom resource definitions
kubectl apply -f https://download.elastic.co/downloads/eck/2.6.1/operator.yaml
```

monitor operator logs:

```sh
ubectl -n elastic-system logs -f statefulset.apps/elastic-operator
```

4. Install Elasticsearch cluster &rarr; see elasticsearch.yaml

5. Install Kibana/Icinga in own namespace &rarr; referenced by elasticsearchRef: name: quickstart; namespace: logging (?)

6. configure logging operator &rarr; see logging.yaml

  * ClusterOutput and ClusterFlow can only be in controlNamespace (logging)

7. configure Elasticsearch output definition &rarr; elasticsearch.yaml

  * user longer timekey in prod to avoid generating too many objects

8. create flow resource &rarr; flow.yaml 

9. install demo app &rarr; demo.yaml

loggin enough with one deployment in namespace logging
