package com.example.utils;

import java.util.Comparator;

import com.example.entities.Joke;

public class JokeComparator implements Comparator<Joke>{

	@Override
	public int compare(Joke o1, Joke o2) {
		if(o1.getDislikes() == 0 && o2.getDislikes() == 0) {
			if(o1.getLikes() > o2.getLikes()) {
				return -1;
			}else {
				return 1;
			}
		}else if(o1.getDislikes() == 0 || o2.getDislikes() == 0) {
			if(o1.getDislikes() == 0) {
				return -1;
			}else {
				return 1;
			}
		}else {
			if(o1.getLikes()/o1.getDislikes() > o2.getLikes()/o2.getDislikes()) {
				return -1;
			}else {
				return 1;
			}
		}
	}

}
