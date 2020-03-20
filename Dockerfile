#Update docker hub link here
FROM dockerhubgeneral.azurecr.io/ui-tests

ARG ENV

RUN ENV=${ENV} ./gradlew uiSuite --offline -Dcucumber.tags="@${ENV}, not @wip, not @bugs"; exit 0

