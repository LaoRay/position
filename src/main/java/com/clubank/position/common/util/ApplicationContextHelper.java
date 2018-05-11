package com.clubank.position.common.util;

import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 设置spring上下文
	 *
	 * @param context
	 *            spring上下文
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		log.info("ApplicationContext-->{}", context);
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T popBean(Class<T> clazz) {
		return Optional.ofNullable(applicationContext).map(context -> context.getBean(clazz)).orElse(null);
	}

	public static <T> T popBean(String name, Class<T> clazz) {
		return Optional.ofNullable(applicationContext).map(context -> context.getBean(name, clazz)).orElse(null);
	}

}
