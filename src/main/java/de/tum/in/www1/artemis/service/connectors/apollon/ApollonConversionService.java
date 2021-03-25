package de.tum.in.www1.artemis.service.connectors.apollon;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.tum.in.www1.artemis.exception.NetworkingError;
import de.tum.in.www1.artemis.web.rest.dto.ApollonConversionDTO;

@Service
@Profile("apollon")
public class ApollonConversionService {

    private final Logger log = LoggerFactory.getLogger(ApollonConversionService.class);

    @Value("${artemis.apollon.conversion-service-url}")
    private String apollonConversionUrl;

    private ApollonConnector connector;

    public ApollonConversionService(RestTemplate apollonRestTemplate) {
        setRestTemplate(apollonRestTemplate);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        connector = new ApollonConnector(log, restTemplate);
    }

    /**
     * Calls the remote Apollon conversion service to convert given model to pdf
     * @param model the model to convert to pdf
     * @return an input stream that is coming from apollon conversion server
     */
    public InputStream convertModel(String model) {

        log.info("Calling Remote Service to convert for model.");
        ApollonConversionDTO apollonDTO = new ApollonConversionDTO();
        apollonDTO.setModel(model);
        try {
            final ApollonConnector.RequestDTO request = new ApollonConnector.RequestDTO(model);
            return connector.invoke(apollonConversionUrl + "/pdf", request);
        }
        catch (NetworkingError networkingError) {
            log.error("Error while calling Remote Service: {}", networkingError.getMessage());
        }
        return null;

    }

}