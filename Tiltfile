SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='showpune/petclinic-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='my-dev')
OUTPUT_TO_NULL_COMMAND = os.getenv("OUTPUT_TO_NULL_COMMAND", default=' > $null ')
update_settings ( max_parallel_updates = 1 , k8s_upsert_timeout_secs = 240 , suppress_unused_image_warnings = None )
allow_k8s_contexts('zhiyongli-tanzu-aks-admin')
k8s_custom_deploy(
    'petclinic',
    apply_cmd="tanzu apps workload apply -f config/workload.yaml --debug --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes " +
               OUTPUT_TO_NULL_COMMAND +
               " && kubectl get workload petclinic --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes','./config/workload.yaml'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('petclinic', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'petclinic'}])
