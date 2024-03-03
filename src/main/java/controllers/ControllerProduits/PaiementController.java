package controllers.ControllerProduits;

import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Basket;
import Entity.entitiesProduits.InvoiceGenerator;
import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ServiceBasket;
import Services.UserAdmineServices.MembreService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class PaiementController {

    @FXML
    private TextField anneeExp;

    @FXML
    private TextField carte;

    @FXML
    private TextField cvc;

    @FXML
    private TextField moisExp;

    @FXML
    private Button pay;

    @FXML
        private void Pay(ActionEvent event) throws StripeException, Exception {

            MembreService sc = new MembreService();
            Membre client;

            ServiceBasket sb = new ServiceBasket();

            System.out.println(isNum(moisExp.getText()));
        if ((isValidVisaCardNo(carte.getText()) && (!carte.getText().isEmpty()) && (isNum(carte.getText())))
                && (!moisExp.getText().isEmpty()) && (isNum(moisExp.getText()))
                && (!anneeExp.getText().isEmpty()) && (isNum(anneeExp.getText())) && (parseInt(anneeExp.getText()) >= LocalDate.now().getYear())
                && (!cvc.getText().isEmpty()) && (isNum(cvc.getText()))) {
            float totalCostTTC = (float) sb.get(5).getTotalCostTTC() * 32;
            if (totalCostTTC <= 0) {
                // Handle case where the total cost is not valid
                System.out.println("Invalid total cost: " + totalCostTTC);
                return; // or show an alert, log an error, etc.
            }

// Calculate the charge amount
            float f = totalCostTTC * 32;
            int k = floatToInt(f);

            if (k < 1) {
                // Handle case where the charge amount is less than 1
                System.out.println("Invalid charge amount: " + k);
                return; // or show an alert, log an error, etc.
            }
            System.out.println("Charge Amount: " + k);
            try {
                PaymentStripApi.pay(k);
            }catch (StripeException e){
                e.printStackTrace();
                System.out.println(e);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paiement");
                alert.setContentText("Paiement effectué avec succès \n Génération du fichier PDF");
                Optional<ButtonType> result2 = alert.showAndWait();
                if (result2.get() == ButtonType.OK) {

                    client = sc.readById(4);
                    Basket panier = sb.get(client.getIdUtilisateur());

                    //String pdfFilename;
                    /*JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");
                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        pdfFilename = fileToSave.getAbsolutePath();
                        System.out.println("Save as file: " + pdfFilename);
                    } else {
                        // User canceled the file chooser
                        return;
                    }
                    try {*/
                    // Create a FileChooser
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save PDF File");

                    // Set extension filter
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                    fileChooser.getExtensionFilters().add(extFilter);

                    // Show save file dialog
                    Stage stage = (Stage) pay.getScene().getWindow(); // Assuming 'pay' is a UI control in your scene
                    File fileToSave = fileChooser.showSaveDialog(stage);

                    if (fileToSave != null) {
                        String pdfFilename = fileToSave.getAbsolutePath();
                        System.out.println("Save as file: " + pdfFilename);

                        try {

                            OutputStream file = new FileOutputStream(new File(pdfFilename));
                            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                            com.itextpdf.text.pdf.PdfWriter.getInstance(document, file);

                            // Inserting Image in PDF
                            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("src/resources/logo.jpg");// Header
                            // Image
                            image.scaleAbsolute(445f, 100.5f);// image width,height

                            PdfPTable irdTable = new PdfPTable(2);
                            irdTable.addCell(InvoiceGenerator.getIRDCell("N° facture"));
                            irdTable.addCell(InvoiceGenerator.getIRDCell("Date facture"));
                            irdTable.addCell(InvoiceGenerator.getIRDCell("XE1234"));
                            LocalDateTime currentDateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDateTime = currentDateTime.format(formatter);
                            irdTable.addCell(InvoiceGenerator.getIRDCell(formattedDateTime + "")); // pass invoice date

                            PdfPTable irhTable = new PdfPTable(3);
                            irhTable.setWidthPercentage(100);

                            irhTable.addCell(InvoiceGenerator.getIRHCell("", PdfPCell.ALIGN_RIGHT));
                            irhTable.addCell(InvoiceGenerator.getIRHCell("", PdfPCell.ALIGN_RIGHT));
                            irhTable.addCell(InvoiceGenerator.getIRHCell("Facture", PdfPCell.ALIGN_RIGHT));
                            irhTable.addCell(InvoiceGenerator.getIRHCell("", PdfPCell.ALIGN_RIGHT));
                            irhTable.addCell(InvoiceGenerator.getIRHCell("", PdfPCell.ALIGN_RIGHT));
                            PdfPCell invoiceTable = new PdfPCell(irdTable);
                            invoiceTable.setBorder(0);
                            irhTable.addCell(invoiceTable);

                            FontSelector fs = new FontSelector();
                            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
                                    com.itextpdf.text.Font.BOLD);
                            fs.addFont(font);
                            Phrase bill = fs.process("Facture à"); // customer information
                            com.itextpdf.text.Paragraph name = new com.itextpdf.text.Paragraph(
                                    client.getPrenomUtilisateur() + " " + client.getNomUtilisateur()); // cl.getPrenom()+ " " +cl.getNom()
                            name.setIndentationLeft(20);
                            com.itextpdf.text.Paragraph contact = new com.itextpdf.text.Paragraph("");
                            contact.setIndentationLeft(20);
                            com.itextpdf.text.Paragraph address = new com.itextpdf.text.Paragraph(
                                    "Adresse: " + client.getPays()); // +cl.getAddress()
                            address.setIndentationLeft(20);

                            PdfPTable billTable = new PdfPTable(6); // one page contains 15 records
                            billTable.setWidthPercentage(100);
                            billTable.setWidths(new float[]{1, 2, 5, 2, 1, 2});
                            billTable.setSpacingBefore(30.0f);
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Ref"));
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Article"));
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Description"));
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Dimension"));
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Quant"));
                            billTable.addCell(InvoiceGenerator.getBillHeaderCell("Prix"));

                            int pos = 1;
                            for (Produits article : panier.getArticles()) {

                                billTable.addCell(InvoiceGenerator.getBillRowCell(pos++ + ""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(article.getLabelle()));
                                billTable.addCell(InvoiceGenerator.getBillRowCell("Piece d'art"));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(article.getDescription() + ""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell("x1"));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(article.getPrix() + " DT"));
                            }

                            for (int i = 0; i <= 4; i++) {
                                billTable.addCell(InvoiceGenerator.getBillRowCell(" "));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(""));
                                billTable.addCell(InvoiceGenerator.getBillRowCell(""));
                            }

                            PdfPTable validity = new PdfPTable(1);
                            validity.setWidthPercentage(100);
                            validity.addCell(InvoiceGenerator.getValidityCell(" "));
                            validity.addCell(InvoiceGenerator.getValidityCell("Garantie"));
                            validity.addCell(InvoiceGenerator.getValidityCell(
                                    " * Les articles achetés sont livrés avec une garantie d'un an \n (si applicable)"));
                            PdfPCell summaryL = new PdfPCell(validity);
                            summaryL.setColspan(3);
                            summaryL.setPadding(1.0f);
                            billTable.addCell(summaryL);

                            PdfPTable accounts = new PdfPTable(2);
                            accounts.setWidthPercentage(100);
                            accounts.addCell(InvoiceGenerator.getAccountsCell("Sous total"));
                            accounts.addCell(InvoiceGenerator.getAccountsCellR(panier.getTotalCost() + " DT"));
                            accounts.addCell(InvoiceGenerator.getAccountsCell("Tax (2.5%)"));
                            accounts.addCell(InvoiceGenerator.getAccountsCellR(panier.getTotalCost() * 0.025 + " DT"));
                            accounts.addCell(InvoiceGenerator.getAccountsCell("Total"));
                            accounts.addCell(InvoiceGenerator.getAccountsCellR(panier.getTotalCostTTC() + " DT"));
                            PdfPCell summaryR = new PdfPCell(accounts);
                            summaryR.setColspan(3);
                            billTable.addCell(summaryR);

                            PdfPTable describer = new PdfPTable(1);
                            describer.setWidthPercentage(100);
                            describer.addCell(InvoiceGenerator.getdescCell(" "));

                            document.open();// PDF document opened........

                            document.add(image);
                            document.add(irhTable);
                            document.add(bill);
                            document.add(name);
                            document.add(contact);
                            document.add(address);
                            document.add(billTable);
                            document.add(describer);

                            document.close();

                            file.close();
                            System.out.println("Pdf created successfully..");
                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        return;
                    }

                }
                alert.show();

            } else {
            System.out.println("Validation failed. Check the values:");
            System.out.println("Carte: " + carte.getText());
            System.out.println("Mois Exp: " + moisExp.getText());
            System.out.println("Annee Exp: " + anneeExp.getText());
            System.out.println("CVC: " + cvc.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement");
            alert.setContentText("Remplir les champs convenablement.");
            alert.show();
        }
        }

    private boolean isValidVisaCardNo(String text) {
        // Remove spaces from the card number
        text = text.replaceAll("\\s", "");

        // Regex to check valid Visa Card number
        String regex = "^4[0-9]{12}(?:[0-9]{3})?$";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // Convert the string to CharSequence
        CharSequence cs = CharBuffer.wrap(text);

        // Find a match between the given string and the regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(cs);

        // Return true if the string matches the regex
        return m.matches();
    }
        public static boolean isNum(String str) {
            String expression = "\\d+";
            return str.matches(expression);
        }

        public static int floatToInt(float value) {
            return (int) value;
        }





}
