![](https://img.shields.io/badge/STATUS-NOT%20CURRENTLY%20MAINTAINED-red.svg?longCache=true&style=flat)

# Important Notice
This public repository is read-only and no longer maintained.

# cloud-spring-boot-sample

Sample application demoing Spring Boot running on SAP HANA Cloud Platform.

Quick start
-----------

Clone the repo, `https://github.com/SAP/cloud-spring-boot-sample.git`, or [download the latest release](https://github.com/SAP/cloud-spring-boot-sample/archive/master.zip).

To run the app stand-alone with an embedded Tomcat simply run the following command:

``mvn spring-boot:run -Drun.profiles=dev,standalone``

To create a `WAR` file that can be deployed to **SAP HANA Cloud Platform** (Tomcat 8 runtime) execute the following command:

``mvn -P neo clean package install``

> **NOTE:** If you deploy the app o **SAP HANA Cloud Platform** do not forget to specify the following JVM argument `-Dspring.profiles.active=neo`.  

> **NOTE:** As outlined in the [official online documentation](https://help.hana.ondemand.com/help/frameset.htm?e6e8ccd3bb571014b6afdc54744eef4d.html) the assembled `WAR` file MUST not include any `SLF4J` or `logback` related jar files, hence we flag the `spring-boot-starter-logging` dependency as `provided`(by the runtime).


To create a `jar` file that can be deployed to **SAP HANA Cloud Platform, Starter Edition for Cloud Foundry Services (Beta)** execute the following command:

``mvn -P cf clean package install``



Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, the SAP HANA Cloud Platform -Samples project will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the following format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major (and resets the minor and patch)
* New additions without breaking backward compatibility bumps the minor (and resets the patch)
* Bug fixes and misc changes bumps the patch

For more information on SemVer, please visit http://semver.org/


Authors
-------

**Matthias Steiner**

+ http://twitter.com/steinermatt
+ http://github.com/steinermatt


Copyright and license
---------------------

Copyright (c) 2016 SAP SE

Except as provided below, this software is licensed under the Apache License, Version 2.0 (the "License"); you may not use this software except in compliance with the License.You may obtain a copy of the License at:

[http://www.apache.org/licenses/LICENSE-2.0] (http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


