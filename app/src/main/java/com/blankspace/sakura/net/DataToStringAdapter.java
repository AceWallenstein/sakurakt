package com.blankspace.sakura.net;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;

import okio.Buffer;

class DataToStringAdapter {
    @ToJson
    void toJson(JsonWriter writer, @DataString String string) throws IOException {
        // Write raw JSON string
        writer.value(new Buffer().writeUtf8(string));
    }

    @FromJson
    @DataString
    String fromJson(JsonReader reader, JsonAdapter<Object> delegate) throws IOException {
        // Now the intermediate data object (a Map) comes here
        Object data = reader.readJsonValue();
        // Just delegate to JsonAdapter<Object>, so we got a JSON string of the object
        return delegate.toJson(data);
    }
}
