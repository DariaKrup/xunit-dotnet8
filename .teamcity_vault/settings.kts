import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.projectFeatures.hashiCorpVaultConnection
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

version = "2024.07"

project {

    buildType(Build)

    features {
        hashiCorpVaultConnection {
            id = "hashicorpVaultConnection1"
            name = "HashiCorp Vault (Local)"
            url = "https://localhost:8200"
            authMethod = appRole {
                roleId = "e0d9ef3e-a837-c70c-ea96-46e9870e6567"
                secretId = "credentialsJSON:48cd3827-a9c5-420c-ab72-3957ed2da18a"
            }
            failOnError = false
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
            dockerImage = "mcr.microsoft.com/dotnet/sdk:8.0"
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
})
