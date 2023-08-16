package org.apache.kafka.connect.storage;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Width;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaSapSoapTemplateConverterConfig extends ConverterConfig {

  public static final String ENCODING_CONFIG = "converter.encoding";
  public static final String ENCODING_DEFAULT = StandardCharsets.UTF_8.name();
  private static final String ENCODING_DOC = "The name of the Java character set to use for encoding strings as byte arrays.";
  private static final String ENCODING_DISPLAY = "Encoding";

  public static final String SAP_LOGIN_USER_CONFIG = "sap.login.user";
  public static final String SAP_LOGIN_USER_DEFAULT = "kafka";
  public static final String SAP_LOGIN_USER_DOC = "The user id for SAP MII basic auth.";
  public static final String SAP_LOGIN_USER_DISPLAY = "SAP user id";

  public static final String SAP_LOGIN_PASSWORD_CONFIG = "sap.login.password";
  public static final String SAP_LOGIN_PASSWORD_DEFAULT = "confluent";
  public static final String SAP_LOGIN_PASSWORD_DOC = "The password for SAP MII basic auth.";
  public static final String SAP_LOGIN_PASSWORD_DISPLAY = "SAP user password";

  public static final String SAP_XML_TEMPLATE_LOCATION_CONFIG = "sap.xml.template.location";
  public static final String SAP_XML_TEMPLATE_LOCATION_DEFAULT = "template/kafkaSapXmlTemplate.xml";
  public static final String SAP_XML_TEMPLATE_LOCATION_DOC = "The Velocity xml location URL.";
  public static final String SAP_XML_TEMPLATE_LOCATION_DISPLAY = "Xml template URL";

  private final static ConfigDef CONFIG;

  static {
    CONFIG = ConverterConfig.newConfigDef();
    CONFIG.define(ENCODING_CONFIG, Type.STRING, ENCODING_DEFAULT, Importance.HIGH, ENCODING_DOC, null, -1, Width.MEDIUM,
        ENCODING_DISPLAY);
    CONFIG.define(SAP_LOGIN_USER_CONFIG, Type.STRING, SAP_LOGIN_USER_DEFAULT, Importance.HIGH, SAP_LOGIN_USER_DOC,
        null, -1, Width.MEDIUM, SAP_LOGIN_USER_DISPLAY);
    CONFIG.define(SAP_LOGIN_PASSWORD_CONFIG, Type.STRING, SAP_LOGIN_PASSWORD_DEFAULT, Importance.HIGH, SAP_LOGIN_PASSWORD_DOC,
        null, -1, Width.MEDIUM, SAP_LOGIN_PASSWORD_DISPLAY);
    CONFIG.define(SAP_XML_TEMPLATE_LOCATION_CONFIG, Type.STRING, SAP_XML_TEMPLATE_LOCATION_DEFAULT, Importance.HIGH, SAP_XML_TEMPLATE_LOCATION_DOC,
        null, -1, Width.MEDIUM, SAP_XML_TEMPLATE_LOCATION_DISPLAY);
  }

  public static ConfigDef configDef() {
    return CONFIG;
  }

  public KafkaSapSoapTemplateConverterConfig(Map<String, ?> props) {
    super(CONFIG, props);
  }

  public String sap_userid() { return getString(SAP_LOGIN_USER_CONFIG); }

  public String sap_password() { return getString(SAP_LOGIN_PASSWORD_CONFIG); }

  public String sap_xml_url() {
    return getString(SAP_XML_TEMPLATE_LOCATION_CONFIG);
  }

  public String encoding() {
    return getString(ENCODING_CONFIG);
  }

}
