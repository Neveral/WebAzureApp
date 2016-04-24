package logic;

import com.microsoft.azure.management.resources.DeploymentOperations;
import com.microsoft.azure.management.resources.ResourceManagementClient;
import com.microsoft.azure.management.resources.ResourceManagementService;
import com.microsoft.azure.management.resources.models.DeploymentExtended;
import com.microsoft.azure.management.resources.models.ResourceGroupExtended;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.core.utils.KeyStoreType;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.management.ManagementClient;
import com.microsoft.windowsazure.management.ManagementService;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;
import com.microsoft.windowsazure.management.models.LocationsListResponse;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    static String uri = "https://management.core.windows.net/";
    static String subscriptionId = "f4ff0f9b-96bb-4fde-adc2-8235f21e8acd";
    static String keyStoreLocation = "WindowsAzureKeyStore.jks";
    static String keyStorePassword = "qwerty";

    public static void main(String[] args) throws IOException, URISyntaxException, ServiceException, ParserConfigurationException, SAXException {
        Configuration config = ManagementConfiguration.configure(
                new URI(uri),
                subscriptionId,
                keyStoreLocation, // the file path to the JKS
                keyStorePassword, // the password for the JKS
                KeyStoreType.jks // flags that I'm using a JKS keystore
        );



// create a management client to call the API
        ManagementClient client = ManagementService.create(config);

// get the list of regions
        LocationsListResponse response = client.getLocationsOperations().list();
        ArrayList<LocationsListResponse.Location> locations = response.getLocations();
//        System.out.println(response.getStatusCode());
//        System.out.println(response.getRequestId());


// write them out
        for( int i=0; i<locations.size(); i++){
            System.out.println(locations.get(i).getDisplayName());
        }
    }
}