package logic;


import com.microsoft.azure.management.compute.ComputeManagementClient;
import com.microsoft.azure.management.compute.ComputeManagementService;
import com.microsoft.azure.management.network.NetworkResourceProviderClient;
import com.microsoft.azure.management.network.NetworkResourceProviderService;
import com.microsoft.azure.management.network.models.PublicIpAddress;
import com.microsoft.azure.management.network.models.VirtualNetwork;
import com.microsoft.azure.management.resources.ResourceManagementClient;
import com.microsoft.azure.management.resources.ResourceManagementService;
import com.microsoft.azure.management.storage.StorageManagementClient;
import com.microsoft.azure.management.storage.StorageManagementService;
import com.microsoft.azure.utility.ComputeHelper;
import com.microsoft.azure.utility.NetworkHelper;
import com.microsoft.azure.utility.ResourceContext;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.core.utils.KeyStoreType;
import com.microsoft.windowsazure.management.ManagementClient;
import com.microsoft.windowsazure.management.ManagementService;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;



import java.net.URI;

public class CreateVirtualNetworkExample {

    static String uri = "https://management.core.windows.net/";
    static String subscriptionId = "f4ff0f9b-96bb-4fde-adc2-8235f21e8acd";
    static String keyStoreLocation = "WindowsAzureKeyStore.jks";
    static String keyStorePassword = "qwerty";
    public static void main(String[] args) throws Exception {



        Configuration config = ManagementConfiguration.configure(
                new URI(uri),
                subscriptionId,
                keyStoreLocation, // the file path to the JKS
                keyStorePassword, // the password for the JKS
                KeyStoreType.jks // flags that I'm using a JKS keystore
        );


        ResourceManagementClient resourceManagementClient = ResourceManagementService.create(config);
        StorageManagementClient storageManagementClient = StorageManagementService.create(config);
        ComputeManagementClient computeManagementClient = ComputeManagementService.create(config);
        NetworkResourceProviderClient networkResourceProviderClient = NetworkResourceProviderService.create(config);

        String resourceGroupName = "javasampleresourcegroup";
        String region = "NorthEurope";

        ResourceContext context = new ResourceContext (
                region, resourceGroupName, subscriptionId, false);

        ComputeHelper.createOrUpdateResourceGroup(resourceManagementClient, context);

        context.setVirtualNetworkName("javasamplevirtualnet");
        VirtualNetwork virtualNetwork = new VirtualNetwork(region);
        context.setVirtualNetwork(virtualNetwork);
        context.setPublicIpAddress(new PublicIpAddress());

        try {
            System.out.println("Start create Virtual Network...");
            NetworkHelper.createVirtualNetwork(networkResourceProviderClient, context);
            System.out.println("Virtual Network created");

            System.out.println("Start create Public IP...");
            NetworkHelper.createPublicIpAddress(networkResourceProviderClient, context);
            System.out.println("Public IP created");

            System.out.println("Start create VM...");
            ComputeHelper.createVM(
                    resourceManagementClient, computeManagementClient, networkResourceProviderClient, storageManagementClient,
                    context, "javaSampleVM", "Foo12", "BaR@123rgababaab")
                    .getVirtualMachine();
            System.out.println("VM created");

            // Remove the resource group will remove all assets (VM/VirtualNetwork/Storage Account/Availability Set etc.)
            // Comment the following line to keep availability set
            resourceManagementClient.getResourceGroupsOperations().beginDeleting(context.getResourceGroupName());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
