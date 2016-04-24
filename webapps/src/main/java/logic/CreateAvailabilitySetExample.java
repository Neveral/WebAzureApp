package logic;

import com.microsoft.azure.management.compute.ComputeManagementClient;
import com.microsoft.azure.management.compute.ComputeManagementService;
import com.microsoft.azure.management.resources.ResourceManagementClient;
import com.microsoft.azure.management.resources.ResourceManagementService;
import com.microsoft.azure.utility.ComputeHelper;
import com.microsoft.azure.utility.ResourceContext;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;
import logic.AzureConfiguration;

public class CreateAvailabilitySetExample {
    public static void main(String[] args) throws Exception {
        Configuration config = AzureConfiguration.createConfiguration();

        ComputeManagementClient computeManagementClient = ComputeManagementService.create(config);
        ResourceManagementClient resourceManagementClient = ResourceManagementService.create(config);

        String resourceGroupName = "Group2";
        String region = "westeurope";

        ResourceContext context = new ResourceContext(
                region, resourceGroupName, System.getenv(ManagementConfiguration.SUBSCRIPTION_ID), false);
        context.setAvailabilitySetName("javasampleavailabilityset");

        ComputeHelper.createOrUpdateResourceGroup(resourceManagementClient, context);

        ComputeHelper.createAvailabilitySet(computeManagementClient, context);

        // Remove the resource group will remove all assets (VM/VirtualNetwork/Storage Account/Availability Set etc.)
        // Comment the following line to keep availability set
        //resourceManagementClient.getResourceGroupsOperations().beginDeleting(context.getResourceGroupName());
    }
}
