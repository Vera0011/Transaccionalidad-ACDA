package com.vera.dto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UsersDTO {
    private String full_name;
    private String user;
    private String email;
    private String password;
    private String creation_date;
    private String modification_date;

    public UsersDTO(String full_name, String user, String email, String password) {
        this.full_name = full_name;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public UsersDTO()
    {
        this.generateRandomData();
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    private void generateRandomData() {
        HashMap<String, ArrayList<String>> data = this.xmlReader();
        Random randomNumber = new Random();
        String user = (data.get("names").get(randomNumber.nextInt(data.get("names").size() - 1) + 1) + "_" + data.get("surnames").get(randomNumber.nextInt(data.get("surnames").size() - 1 ) + 1)).toLowerCase();
        String full_name = user.replace("_", " ");

        this.full_name = full_name;
        this.user = user;
        this.email = user + "@test.com";
        this.password = generateRandomPassword();
    }

    private HashMap<String, ArrayList<String>> xmlReader() {
        HashMap<String, ArrayList<String>> resultMap = new HashMap<>();
        String pathXML = "src/com/vera/dto/src/basic_info.xml";
        File xmlFile = new File(pathXML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeListUser = doc.getElementsByTagName("persona");
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> surnames = new ArrayList<>();

            for (int i = 0; i < nodeListUser.getLength(); i++) {
                names.add(((Element) nodeListUser.item(i)).getElementsByTagName("nombre").item(0).getTextContent());
                surnames.add(((Element) nodeListUser.item(i)).getElementsByTagName("apellido").item(0).getTextContent());
            }

            resultMap.put("names", names);
            resultMap.put("surnames", surnames);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error cargando fichero XML");
        }

        return resultMap;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "full_name='" + this.full_name + '\'' +
                ", user='" + this.user + '\'' +
                ", email='" + this.email + '\'' +
                ", password='" + this.password + '\'' +
                ", creation_date='" + this.creation_date + '\'' +
                ", modification_date='" + this.modification_date + '\'' +
                "}\n";
    }

    private String generateRandomPassword()
    {
        String password = "";

        for (int i = 0; i < 4; i++)
        {
            Random rnd = new Random();

            password += rnd.nextInt(10);
        }

        return password;
    }
}
