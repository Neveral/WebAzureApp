package servlets;

import com.microsoft.azure.management.storage.StorageManagementClient;
import com.microsoft.azure.management.storage.StorageManagementService;
import com.microsoft.windowsazure.Configuration;
import logic.AzureConfiguration;
import logic.CreateStorageAccount;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateStorageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String storageName = req.getParameter("name");
        if (storageName == null) storageName = "javasimplestorage";
        if (storageName == "") storageName = "javasimplestorage";
        try{
            Configuration config = AzureConfiguration.createConfiguration();
            StorageManagementClient storageManagementClient = StorageManagementService.create(config);

            String group = "Group2";
            String accType = "standard_grs";
            String location = "westeurope";
            CreateStorageAccount.createStorageAccount(storageManagementClient, group, storageName, accType, location);


            req.setAttribute("group", group);
            req.setAttribute("name", storageName);
            req.setAttribute("accType", accType);
            req.setAttribute("location", location);
        }catch(NoSuchFieldError | Exception ex){
            ex.printStackTrace();
        }


        req.getRequestDispatcher("/storage.jsp").forward(req, resp);
    }

}
