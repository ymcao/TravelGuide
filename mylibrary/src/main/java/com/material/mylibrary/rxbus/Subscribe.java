/**
 * 
 */
package com.material.mylibrary.rxbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息的订阅者默认使用ThreadMode.PostThread（在当前的thread运行）
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
	ThreadMode value() default ThreadMode.PostThread;
}
