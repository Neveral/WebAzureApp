package logic;

import com.microsoft.azure.management.storage.StorageManagementClient;
import com.microsoft.azure.management.storage.models.AccountType;
import com.microsoft.azure.management.storage.models.StorageAccount;
import com.microsoft.azure.utility.ResourceContext;
import com.microsoft.azure.utility.StorageHelper;
import logic.AzureConfiguration;

import java.util.List;

public class CreateStorageAccount {
    public static void main(String[] args) throws Exception{
        //Configuration config = AzureConfiguration.createConfiguration();
        //StorageManagementClient storageManagementClient = StorageManagementService.create(config);

        //createStorageAccount(storageManagementClient, "Group2", "Storage1", "standard_grs", "westeurope");
        //listAccounts(storageManagementClient, "Group2");
    }
    public static void createStorageAccount(StorageManagementClient client,
                                            String resourceGroup, String accountName,
                                            String accountType, String location)
            throws Exception {
        AccountType accountTypeVal = accountTypeStrToVal(accountType);


        ResourceContext context = new ResourceContext(location, resourceGroup, AzureConfiguration.subscriptionId, false);
        context.setStorageAccountName(accountName);
        //StorageAccountCreateParameters stoInput = new StorageAccountCreateParameters((accountTypeVal,location);

        StorageAccount account = null;
        try{
            account = StorageHelper.createStorageAccount(client, context);
        }catch (NoSuchFieldError ex){
            System.out.println(ex.getMessage());
        }

            System.out.println("Creation of storage account succeeded: " + accountName);


    }

    private static void listAccounts(StorageManagementClient client, String resourceGroup) throws Exception {
        List<StorageAccount> accounts = null;
        accounts = StorageHelper.listStorageAccounts(client, null);
        if(accounts != null) {
            System.out.println("Storage accounts for " +
                    (resourceGroup == null ? "subscription " + AzureConfiguration.subscriptionId :
                            "resource group " + resourceGroup) + ": ");
            for (StorageAccount account : accounts) {
                System.out.println("  " + account.getName());
            }
        }
    }

    private static AccountType accountTypeStrToVal(String accountType) {
        if(accountType.toLowerCase().equals("standard_grs")) {
            return AccountType.StandardGRS;
        } else if(accountType.toLowerCase().equals("standard_ragrs")) {
            return AccountType.StandardRAGRS;
        } else if(accountType.toLowerCase().equals("standard_lrs")) {
            return AccountType.StandardLRS;
        } else if(accountType.toLowerCase().equals("standard_zrs")) {
            return AccountType.StandardZRS;
        } else if(accountType.toLowerCase().equals("premium_lrs")) {
            return AccountType.PremiumLRS;
        }

        return null;
    }
}
