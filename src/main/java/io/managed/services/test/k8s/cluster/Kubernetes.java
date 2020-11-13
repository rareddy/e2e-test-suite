package io.managed.services.test.k8s.cluster;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.managed.services.test.executor.Exec;
import io.managed.services.test.k8s.KubeClient;
import io.managed.services.test.k8s.cmdClient.KubeCmdClient;
import io.managed.services.test.k8s.cmdClient.Kubectl;
import io.managed.services.test.k8s.exceptions.KubeClusterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link KubeCluster} implementation for any {@code Kubernetes} cluster.
 */
public class Kubernetes implements KubeCluster {

    public static final String CMD = "kubectl";
    private static final String OLM_NAMESPACE = "operators";
    private static final Logger LOGGER = LogManager.getLogger(Kubernetes.class);

    @Override
    public boolean isAvailable() {
        return Exec.isExecutableOnPath(CMD);
    }

    @Override
    public boolean isClusterUp() {
        try {
            return Exec.exec(CMD, "cluster-info").exitStatus() && !Exec.exec(CMD, "api-resources").out().contains("openshift.io");
        } catch (KubeClusterException e) {
            LOGGER.debug("Error:", e);
            return false;
        }
    }

    @Override
    public KubeCmdClient defaultCmdClient() {
        return new Kubectl();
    }

    @Override
    public KubeClient defaultClient() {
        return new KubeClient(new DefaultKubernetesClient(CONFIG), "default");
    }

    public String toString() {
        return CMD;
    }

    @Override
    public String defaultOlmNamespace() {
        return OLM_NAMESPACE;
    }
}