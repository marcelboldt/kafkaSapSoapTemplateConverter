package org.apache.kafka.connect.storage;

import org.apache.commons.text.StringSubstitutor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.errors.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class KafkaSapSoapTemplateConverter extends StringConverter{

  private static final Logger log = LoggerFactory.getLogger(KafkaSapSoapTemplateConverter.class);


  private final StringDeserializer deserializer = new StringDeserializer();

  StringWriter sw = new StringWriter();
  String template;
  Map<String, String> valuesMap;



  @Override
  public void configure(Map<String, ?> configs) {
    super.configure(configs);

    KafkaSapSoapTemplateConverterConfig conf = new KafkaSapSoapTemplateConverterConfig(configs);

    valuesMap = new HashMap<String, String>();
    valuesMap.put("login_name", conf.sap_userid());
    valuesMap.put("login_password", conf.sap_password());

    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(conf.sap_xml_url());
    try {
      template = readFromInputStream(inputStream);
    } catch (Exception e) {
      log.warn("Failed to deserialize string: ", e.toString());
    }

  }

  @Override
  public SchemaAndValue toConnectData(String topic, byte[] value) {
    try {
      return new SchemaAndValue(Schema.OPTIONAL_STRING_SCHEMA, stringReplace(deserializer.deserialize(topic, value)));
    } catch (SerializationException e) {
      throw new DataException("Failed to deserialize string: ", e);
    }
  }


  private String readFromInputStream(InputStream inputStream)
      throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br
             = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }
    }
    return resultStringBuilder.toString();
  }

  public String stringReplace(String message) {

    valuesMap.put("message", message);
    //log.info("Values: " + valuesMap.toString());
    StringSubstitutor sub = new StringSubstitutor(valuesMap);
    String substituted = sub.replace(template);
    //log.info("Substituted: " + substituted);

    return substituted;
  }
}
