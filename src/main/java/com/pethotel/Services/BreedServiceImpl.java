package com.pethotel.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.pethotel.DTO.BreedResponse;

@Service
public class BreedServiceImpl implements BreedService {
	private final RestClient restClient;
	
	private final String API_KEY = "live_3fONWXO6KSM441jIevOvAv0e0Kr9nDkuGeSwfmnGyQlDVcd1xs8N3DsCi1zPAVFb";
	
    public BreedServiceImpl(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.thedogapi.com/v1")
                .build();
    }

    @Override
    public boolean isValidBreed(String species, String breed) {

        if (!species.toUpperCase().equals("DOG")) {
            return false;
        }

        BreedResponse[] breeds = restClient.get()
                .uri("/breeds/search?q={breed}", breed)
                .header("x-api-key", API_KEY)
                .retrieve()
                .body(BreedResponse[].class);

        if (breeds == null || breeds.length == 0) {
        	return false;
        }
        
        for (BreedResponse breedDto : breeds) {
        	if (breedDto.getName().toUpperCase().equals(breed.toUpperCase())) {
        		return true;
        	}
        }
        
        return false;
    }
}
