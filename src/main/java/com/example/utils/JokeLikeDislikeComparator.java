package com.example.utils;

import java.util.Comparator;

import com.example.entities.Joke;

public class JokeLikeDislikeComparator implements Comparator<Joke>{

	@Override
	public int compare(Joke o1, Joke o2) {
		if(o1.getUsersDisliked().size() == 0 && o2.getUsersDisliked().size() == 0) {
			if(o1.getUsersLiked().size() > o2.getUsersLiked().size()) {
				return -1;
			}else {
				return 1;
			}
		}else if(o1.getUsersDisliked().size() == 0 || o2.getUsersDisliked().size() == 0) {
			if(o1.getUsersLiked().size() == 0) {
				return -1;
			}else {
				return 1;
			}
		}else {
			if(o1.getUsersLiked().size()/o1.getUsersDisliked().size() > o2.getUsersLiked().size()/o2.getUsersDisliked().size()) {
				return -1;
			}else {
				return 1;
			}
		}
	}

}
