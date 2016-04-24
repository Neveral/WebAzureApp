package logic;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.azure.management.resources.ResourceManagementClient;
import com.microsoft.azure.management.resources.ResourceManagementService;
import com.microsoft.azure.management.resources.models.ResourceGroupExtended;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;

import javax.naming.ServiceUnavailableException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Service Principal Example
 * <p>
 * This example will show you how to make a request to Azure Resource Manager (ARM) using a ADAL4J and Azure Active
 * Directory. You will need the following:
 * <p>
 * <ol>
 * <li>Your Azure Active Directory Tenant ID</li>
 * <li>Your Azure Active Directory Subscription ID</li>
 * <li>Your Azure Active Directory Application Client ID</li>
 * <li>Your Azure Active Directory Application Client Secret</li>
 * </ol>
 * <p>
 * After gathering or creating each of those items, you should be able to add them to the source code below to
 * execute your first authenticated request using a Service Principal with RBAC in Azure. Each of the items below
 * are
 *
 * @see <a href="https://azure.microsoft.com/en-us/documentation/articles/resource-group-create-service-principal-portal/">Creating a Service Principal</a>
 * </p>
 */
public class ServicePrincipalExample {

    /**
     * Request a listing of all resource groups within a subscription using a service principal for authentication.
     *
     * @param args arguments supplied at the command line (they are not used)
     * @throws Exception all of the exceptions!!
     */
    public static void main(String[] args) throws Exception {
        ResourceManagementClient client = ServicePrincipalExample.createResourceManagementClient();
        ArrayList<ResourceGroupExtended> groups = client.getResourceGroupsOperations().list(null).getResourceGroups();

        System.out.println("Resources:");
        for (ResourceGroupExtended group : groups) {
            System.out.println("ID: " + group.getId());
            System.out.println("Name: " + group.getName());
            System.out.println("Location: " + group.getLocation());
            //System.out.println("Properties: " + group.getProperties());
        }
    }

    /**
     * Use the ResourceManagementService factory helper method to create a client based on the management config.
     *
     * @return ResourceManagementClient a client to be used to make authenticated requests to the ARM REST API
     * @throws Exception all of the exceptions
     */
    protected static ResourceManagementClient createResourceManagementClient() throws Exception {
        Configuration config = createConfiguration();
        return ResourceManagementService.create(config);
    }

    /**
     * Create configuration builds the management configuration needed for creating the ResourceManagementService.
     * The config contains the baseURI which is the base of the ARM REST service, the subscription id as the context for
     * the ResourceManagementService and the AAD token required for the HTTP Authorization header.
     *
     * @return AzureConfiguration the generated configuration
     * @throws Exception all of the exceptions!!
     */
    public static Configuration createConfiguration() throws Exception {
        String baseUri = "https://management.core.windows.net";
        return ManagementConfiguration.configure(
                null,
                new URI(baseUri),
                // TODO: add your subscription id
                "f4ff0f9b-96bb-4fde-adc2-8235f21e8acd",
                getAccessTokenFromServicePrincipalCredentials().getAccessToken());
    }

    /**
     * Get access token from service principal credentials calls ADAL4J to get a Bearer Auth token to use for the ARM
     * REST API.
     *
     * @return AuthenticationResult the result of the request to Azure Active Directory via ADAL4J
     * @throws ServiceUnavailableException something broke when making a call to Azure Active Directory
     * @throws MalformedURLException       the url provided to AAD was not properly formed
     * @throws ExecutionException          houston we have a problem.
     * @throws InterruptedException        the request to AAD has been interrupted
     */
    private static AuthenticationResult getAccessTokenFromServicePrincipalCredentials() throws
            ServiceUnavailableException, MalformedURLException, ExecutionException, InterruptedException {
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            // TODO: add your tenant id
            context = new AuthenticationContext("https://login.windows.net/" + "24c97588-964b-41b4-a777-c12a76548b20",
                    false, service);
            // TODO: add your client id and client secret
            ClientCredential cred = new ClientCredential("7f302fec-f81f-44c5-b573-2e897c0d64ff",
                    "NfG6uLvMDsyoQxTMMcXS+E6zgEd4mX5/JvgWfozdiOU=");
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://management.azure.com/", cred, null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
}
