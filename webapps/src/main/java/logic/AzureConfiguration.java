package logic;

import com.microsoft.azure.utility.AuthHelper;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;

import java.net.URI;

public class AzureConfiguration {
    static String uri = "https://management.core.windows.net/" ;//"https://management.azure.com/";
    static String subscriptionId = "f4ff0f9b-96bb-4fde-adc2-8235f21e8acd";
    static String keyStoreLocation = "WindowsAzureKeyStore.jks";
    static String keyStorePassword = "qwerty";

    static String clientid = "7f302fec-f81f-44c5-b573-2e897c0d64ff"; // username
    static String clientkey = "NfG6uLvMDsyoQxTMMcXS+E6zgEd4mX5/JvgWfozdiOU="; // password
    static String tenantid = "24c97588-964b-41b4-a777-c12a76548b20"; //endpoint
    static String aadurl = "https://login.windows.net/";
    static String managementuri = "https://management.core.windows.net/";

    public static Configuration createConfiguration() throws Exception {
        String baseUri = uri;
        return ManagementConfiguration.configure(
                null,
                baseUri != null ? new URI(baseUri) : null,
                subscriptionId,
                AuthHelper.getAccessTokenFromServicePrincipalCredentials(
                        managementuri, aadurl,
                        tenantid, clientid,
                        clientkey)
                        .getAccessToken());
    }
}
