# Axway API-Manager Report tool

Use this tool to generate reports for your Axway API-Management V7.x platform. You can create reports such as:

![API Subscription report][1]

This report gives you details insights about the actual API-Subscription and can be further processed into other tools.

Under the hood it is using Swagger-Promote-Core as a library for the API-Manager communication, that retrieves the required Data-Model, which is then easily converted into a report of choice. That way, many different reports can be generated. If you need a special report not yet part of the package, just create an [issue][2].

With that you can export many different information from your API-Manager into different reports.  

Call the script: `run-metadata-export.sh` delivered with the release, which generates the following usage:
```
usage: API-Manager Meta-Data Export -f <api-mgr-prod-metadata-export.csv> [-h <api-host>] [-p <changeme>] [-port <8181>] -r
       <CSVEmbeddedAnalyticsReport> [-u <apiadmin>]
 -f,--filename <api-mgr-prod-metadata-export.csv>   Filename used to export meta-data configuration (relative or absolute).
 -h,--host <api-host>                               The API-Manager hostname the API should be imported
 -p,--password <changeme>                           Password used to authenticate
 -port <8181>                                       Optional parameter to declare the API-Manager port. Defaults to 8075.
 -r,--report <CSVEmbeddedAnalyticsReport>           Type of report you want.
 -u,--username <apiadmin>                           Username used to authenticate. Please note, that this user must have Admin-Role


Tool to query your API-Manager registry and generate reports in some formats.
Originally invented to feed API-Metadata into Axway EA4APIM. Flexible for other formats


The following Report-Formats are supported:
CSVEmbeddedAnalyticsReport:
Generates a report with APIs, Applications, Orgs and their relation to each other. (CSV-Format)
CSVCustomPolicyDependencyReport:
Tells you which APIs are using which Custom-Policies (CSV-Format)
ExcelCustomPolicyDependencyReport:
Tells you which APIs are using which Custom-Policies (Excel-Format)


ERROR: Missing required option: r


You may run one of the following examples:
scripts\run-metadata-export.bat -h localhost -u apiadmin -p changeme -f custom-policies -r ExcelCustomPolicyDependencyReport

For more information visit: https://github.com/Axway-API-Management-Plus/apimanager-swagger-promote/wiki
```

[1]: images/API-Subscription-Report-Excel.png
[2]: issues
