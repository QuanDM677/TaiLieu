FROM tomcat:10.1-jdk17
COPY target/fashion_academic_site.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]