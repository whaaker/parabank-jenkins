# baseline image
FROM parasoft/parabank:baseline

ARG HOST_UID=995
ARG HOST_GID=991

# Setup parasoft user
USER root
ARG USER_HOME_DIR=/usr/local/tomcat

# Create the "parasoft" group
RUN groupadd -g ${HOST_GID} parasoft

# Create the "parasoft" user and add them to the "parasoft" group
RUN useradd -u ${HOST_UID} -g ${HOST_GID} -m -s /bin/bash -c "Parasoft User" parasoft

#RUN groupmod -g ${HOST_GID} parasoft \
#    && usermod -u ${HOST_UID} -g ${HOST_GID} parasoft

RUN chown -h -R ${HOST_UID}:${HOST_GID} ${USER_HOME_DIR}

USER parasoft

# Define working directory.
WORKDIR ${USER_HOME_DIR}

EXPOSE 8080
EXPOSE 8050
EXPOSE 9001
EXPOSE 61616