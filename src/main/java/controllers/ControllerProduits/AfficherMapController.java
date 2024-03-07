    package controllers.ControllerProduits;

    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.web.WebEngine;
    import javafx.scene.web.WebView;

    import java.net.URL;
    import java.util.ResourceBundle;

    public class AfficherMapController   implements Initializable {



        @FXML
        private WebView mapWebView;
        private String productLocation; // Assuming this is the attribute in Produits.java containing the Google Maps URL

        public void setProductLocation(String productLocation) {
            this.productLocation = productLocation;
            loadMap();
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            WebEngine webEngine = mapWebView.getEngine();
            loadMap();
        }
        public void loadMap() {
            WebEngine webEngine = mapWebView.getEngine();

            // Generate HTML content with the correct map URL
            String htmlContent = generateMapHtml(productLocation);

            // Load the HTML content into the WebView
            webEngine.loadContent(htmlContent);
        }

        private String generateMapHtml(String productLocation) {
            // Construct the map URL based on the club name, governorate, and city

            // Generate HTML content with the correct map URL
            return "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Google Maps Example</title>\n" +
                    "    <style>\n" +
                    "        /* Adjust the size and position of the map */\n" +
                    "        #mapouter {\n" +
                    "            position: relative;\n" +
                    "            text-align: right;\n" +
                    "            height: 500px; /* Adjust the height as needed */\n" +
                    "            width: 500px; /* Adjust the width as needed */\n" +
                    "        }\n" +
                    "\n" +
                    "        #gmap_canvas2 {\n" +
                    "            overflow: hidden;\n" +
                    "            background: none !important;\n" +
                    "            height: 500px; /* Adjust the height as needed */\n" +
                    "            width: 500px; /* Adjust the width as needed */\n" +
                    "        }\n" +
                    "\n" +
                    "        #gmap_canvas {\n" +
                    "            width: 100%;\n" +
                    "            height: 100%;\n" +
                    "            border: 0;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div id=\"mapouter\">\n" +
                    "    <div id=\"gmap_canvas2\">\n" +
                    "        <iframe id=\"gmap_canvas\"\n" +
                    "                src=\"" + productLocation + "\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
        }



    }
