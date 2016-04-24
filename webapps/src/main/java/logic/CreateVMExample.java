package logic;

import com.microsoft.azure.management.compute.ComputeManagementClient;
import com.microsoft.azure.management.compute.ComputeManagementService;
import com.microsoft.azure.management.compute.models.NetworkInterfaceReference;
import com.microsoft.azure.management.compute.models.VirtualMachine;
import com.microsoft.azure.management.network.NetworkResourceProviderClient;
import com.microsoft.azure.management.network.NetworkResourceProviderService;
import com.microsoft.azure.management.network.models.NetworkInterface;
import com.microsoft.azure.management.network.models.NetworkInterfaceIpConfiguration;
import com.microsoft.azure.management.network.models.PublicIpAddress;
import com.microsoft.azure.management.resources.ResourceManagementClient;
import com.microsoft.azure.management.resources.ResourceManagementService;
import com.microsoft.azure.management.resources.models.ResourceGroupExtended;
import com.microsoft.azure.management.storage.StorageManagementClient;
import com.microsoft.azure.management.storage.StorageManagementService;
import com.microsoft.azure.utility.ComputeHelper;
import com.microsoft.azure.utility.ResourceContext;
import com.microsoft.windowsazure.Configuration;
import logic.AzureConfiguration;


import java.util.ArrayList;

public class CreateVMExample {




    public static void main(String[] args) throws Exception {
        createVM();
        //listOfVM();
        //System.out.println(AuthHelper.getAccessTokenFromServicePrincipalCredentials(managementuri, aadurl, tenantid, clientid, clientkey).getUserInfo().toString());

    }

    public static void createVM() throws Exception{
        Configuration config = AzureConfiguration.createConfiguration();

        ResourceManagementClient resourceManagementClient = ResourceManagementService.create(config);
        StorageManagementClient storageManagementClient = StorageManagementService.create(config);
        ComputeManagementClient computeManagementClient = ComputeManagementService.create(config);
        NetworkResourceProviderClient networkResourceProviderClient = NetworkResourceProviderService.create(config);

        String resourceGroupName = "Group2";
        String region = "westeurope";

        ResourceContext context = new ResourceContext(
                region, resourceGroupName, AzureConfiguration.subscriptionId, false);

        System.out.println("Start create vm...");

//        Password requirements:
//        1) Contains an uppercase character
//        2) Contains a lowercase character
//        3) Contains a numeric digit
//        4) Contains a special character.

        try {
            VirtualMachine vm = ComputeHelper.createVM (
                    resourceManagementClient, computeManagementClient, networkResourceProviderClient, storageManagementClient,
                    context, "javaSampleVM", "Foo12", "BaR@123rgabhhjOO")
                    .getVirtualMachine();

            System.out.println(vm.getName() + " is created");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // Remove the resource group will remove all assets (VM/VirtualNetwork/Storage Account etc.)
        // Comment the following line to keep the VM.
       //resourceManagementClient.getResourceGroupsOperations().beginDeleting(context.getResourceGroupName());
    }


    public static void listOfVM() throws Exception{
        Configuration config = AzureConfiguration.createConfiguration();
        ResourceManagementClient resourceManagementClient = ResourceManagementService.create(config);
        ComputeManagementClient computeManagementClient = ComputeManagementService.create(config);
        NetworkResourceProviderClient networkResourceProviderClient = NetworkResourceProviderService.create(config);

        ArrayList<ResourceGroupExtended> resourceGroups = resourceManagementClient.getResourceGroupsOperations().list(null).getResourceGroups();
        for (ResourceGroupExtended resourcesGroup : resourceGroups) {
            String rgName = resourcesGroup.getName();

            ArrayList<VirtualMachine> vms = computeManagementClient.getVirtualMachinesOperations().list(rgName).getVirtualMachines();
            System.out.println("Resource Group: " + rgName);

            for (VirtualMachine vm : vms) {
                System.out.println("    VM: " + vm.getName());
                ArrayList<NetworkInterfaceReference> nics = vm.getNetworkProfile().getNetworkInterfaces();

                for (NetworkInterfaceReference nicReference : nics) {
                    String[] nicURI = nicReference.getReferenceUri().split("/");
                    NetworkInterface nic = networkResourceProviderClient.getNetworkInterfacesOperations().get(rgName, nicURI[nicURI.length - 1]).getNetworkInterface();
                    System.out.println("        NIC: " + nic.getName());
                    System.out.println("        Is primary: " + nic.isPrimary());
                    ArrayList<NetworkInterfaceIpConfiguration> ips = nic.getIpConfigurations();

                    for (NetworkInterfaceIpConfiguration ipConfiguration : ips) {
                        System.out.println("        Private IP address: " + ipConfiguration.getPrivateIpAddress());

                        String[] pipID = ipConfiguration.getPublicIpAddress().getId().split("/");
                        PublicIpAddress pip = networkResourceProviderClient.getPublicIpAddressesOperations().get(rgName, pipID[pipID.length - 1]).getPublicIpAddress();
                        System.out.println("        Public IP address: " + pip.getIpAddress());
                    }
                }
            }
        }
    }

    /**
     * Create a storage account.
     *
     * @param client                storage management client
     * @param resourceGroup         name of the resource group
     * @param accountName           name of the storage account
     * @param accountType           type of storage account
     * @param location              location of the storage account
     * @return int                  zero on success, one on failure, minus one for invalid arg
     * @throws Exception            throw all exceptions
     */


    /**
     * Create configuration builds the management configuration needed for creating the clients.
     * The config contains the baseURI which is the base of the ARM REST service, the subscription id as the context for
     * the ResourceManagementService and the AAD token required for the HTTP Authorization header.
     *
     * @return AzureConfiguration the generated configuration
     * @throws Exception all of the exceptions!!
     */

}
