package com.myhomepage.homepage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhomepage.homepage.entity.Actor;
import com.myhomepage.homepage.repository.ActorRepository;
import com.myhomepage.homepage.utility.CommaSeparatedValueConverter;

@Service
public class ActorService {
	
	@Autowired
	private ActorRepository actorRepository;
	
	public List<Actor> findActorsByCommaSeparatedValues(String string) {		
		List<Actor> actors = new ArrayList<>();
		if (string != null && !string.isBlank()) {		
			String[] actorsArray = CommaSeparatedValueConverter.stringToArray(string);
			for (String actorName: actorsArray) {
				Actor actor = actorRepository.findByName(actorName);
				if (actor == null) {
					actor = actorRepository.save(new Actor(actorName));
				}
				actors.add(actor);
			}
		}
		return actors;
	}
	
	public Actor findById(int id) {
		return actorRepository.findById(id).get();
	}

}
