package com.cloudmusic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface BaseService<E> {
	
	public void add(E e);

}
