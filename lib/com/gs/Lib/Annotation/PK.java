/**
 * GS
 */
package com.gs.Lib.Annotation;
import java.lang.annotation.*;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PK {
}
