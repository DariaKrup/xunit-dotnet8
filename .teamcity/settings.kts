import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.11"

project {

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_4"
            type = "OAuthProvider"
            param("role-id", "e0d9ef3e-a837-c70c-ea96-46e9870e6567")
            param("displayName", "HashiCorp Vault Proj")
            param("secure:secret-id", "credentialsJSON:99b9d460-33b2-4ddf-82ec-425774fb7c13")
            param("providerType", "teamcity-vault")
            param("url", "https://vault.burnasheva.click:8200")
        }
    }
}

object Build : BuildType({
    name = "Build"

    params {
        param("docker_pass", "%vault:passwords_storage_v1/docker!/password%")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "PrimeService.Tests/PrimeService.Tests.csproj"
            sdk = "8"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    dependencies {
        artifacts(AbsoluteId("JavaMavenDemoDslKeys_Build")) {
            buildRule = lastSuccessful()
            artifactRules = "*.*"
        }
    }
})
