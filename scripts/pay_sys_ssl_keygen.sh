#It works in Linux, possible in MacOS
keytool -genkey -alias tomcat -keyalg RSA -storepass changeit -keypass changeit -dname 'CN=tomcat'

#For Windows user it may be works --------------------------------------------------------------------
#Try to enter same command (command lies in %JAVA_HOME% path):
# >> keytool -genkey -alias tomcat -keyalg RSA -storepass changeit -keypass changeit -dname 'CN=tomcat'

# If it doesn't work, enter (this command interactively requests some data (name, organization, ...)):
# >> keytool -genkey -alias tomcat -keyalg RSA -storepass changeit -keypass changeit
# ... in user's home directory .keystore file will be created
#-----------------------------------------------------------------------------------------------------

#TomCat Plugin for Windows developers ----------------------------------------------------------------
#Possible problem with maven tomcat7 plugin
#Try to set your path to .keystore file, replace ${user.home} with proper path:
# <plugin>
#   <groupId>org.apache.tomcat.maven</groupId>
#   <artifactId>tomcat7-maven-plugin</artifactId>
#   <version>2.2</version>
#   <configuration>
#       <path>/</path>
#       <httpsPort>8443</httpsPort>
#       <keystoreFile>${user.home}/.keystore</keystoreFile>
#       <keystorePass>changeit</keystorePass>
#   </configuration>
# </plugin>
#-----------------------------------------------------------------------------------------------------

#Settings for Non-Plugin Tomcat ----------------------------------------------------------------------
# 1) .keystore file has been created in previous steps of this file
# 2) Go to <path/to/tomcat>/conf/server.xml and find such lines:
# <!-- Define a SSL/TLS HTTP/1.1 Connector on port 8443
#          This connector uses the NIO implementation. The default
#          SSLImplementation will depend on the presence of the APR/native
#          library and the useOpenSSL attribute of the
#          AprLifecycleListener.
#          Either JSSE or OpenSSL style configuration may be used regardless of
#          the SSLImplementation selected. JSSE style configuration is used below.
# -->
# 3) Insert this lines after previous comment
#  <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
#         maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
#         clientAuth="false" sslProtocol="TLS"
#         keystoreFile="${user.home}/.keystore" keystorePass="changeit" />
# 4) Restart your server and try to enter URL: https://localhost:8443/
#-----------------------------------------------------------------------------------------------------