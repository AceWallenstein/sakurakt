package com.blankspace.sakura.net;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
@JsonQualifier
public @interface DataString {
}
