package builders.assetManagement.assetService;

import entities.responses.assetManagement.assetServices.Properties;

public class PropertiesBuilder {

    Properties properties = new Properties();

    public PropertiesBuilder() {
        properties.setLandName("name of land");
        properties.setLandNumber("12345");
    }

    public PropertiesBuilder withLandName(String LandName) {
        properties.setLandName(LandName);
        return this;
    }

    public PropertiesBuilder withLandNumber(String LandNumber) {
        properties.setLandNumber(LandNumber);
        return this;
    }

    public Properties build() {
        return properties;
    }
}