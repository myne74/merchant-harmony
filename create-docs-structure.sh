#!/bin/bash

mkdir -p docs/{01-product,02-requirements,03-roadmap,04-backlog,05-system-design,06-api,07-database,08-security,09-testing,10-devops,11-operations,12-architecture-decisions,13-release-notes,14-retrospectives,15-project-journal,16-reviews,17-showcase,templates}

touch AI_CONTEXT.md PROJECT_STATE.md README.md .gitignore
touch docs/README.md

touch docs/01-product/{Vision.md,ProblemStatement.md,Goals.md,Personas.md,Scope.md,SuccessMetrics.md}

touch docs/02-requirements/{Requirement_MVP_Detailed.md,requirements_future.md,BusinessRules.md,UserStories.md,AcceptanceCriteria.md,Glossary.md}

touch docs/03-roadmap/{ProductRoadmap.md,Milestones.md,ReleasePlan.md,Risks.md,Dependencies.md}

touch docs/04-backlog/{ProductBacklog.md,SprintBacklog.md,Epics.md,TechnicalDebt.md,IceBox.md}

touch docs/05-system-design/{SystemDesign.md,ComponentDiagram.md,SequenceDiagrams.md,DeploymentDiagram.md,Scalability.md}

touch docs/06-api/{ApiDesign.md,ErrorHandling.md,Versioning.md,OpenApi.md}

touch docs/07-database/{DatabaseDesign.md,ERDiagram.md,DataDictionary.md,IndexStrategy.md,FlywayMigrations.md}

touch docs/08-security/{Authentication.md,Authorization.md,JWTDesign.md,ThreatModel.md,SecurityChecklist.md}

touch docs/09-testing/{TestStrategy.md,TestPlan.md,TestCases.md,IntegrationTesting.md,PerformanceTesting.md,AcceptanceTesting.md}

touch docs/10-devops/{BuildPipeline.md,Docker.md,DeploymentStrategy.md,BranchingStrategy.md,Environments.md}

touch docs/11-operations/{Runbook.md,Monitoring.md,Logging.md,IncidentResponse.md,Troubleshooting.md}

touch docs/12-architecture-decisions/{DecisionLog.md,ADR-001-ServiceSplit.md,ADR-002-JWT-Authentication.md,ADR-003-QR-Code-Strategy.md,ADR-004-Database-Choice.md}

touch docs/13-release-notes/{v0.1.md,v0.2.md,v1.0.md}

touch docs/14-retrospectives/{Sprint-01.md,Sprint-02.md,LessonsLearned.md}

touch docs/15-project-journal/{2026-06-27.md,Decisions.md,OpenQuestions.md}

touch docs/16-reviews/{ArchitectureReview.md,CodeReviewChecklist.md,SecurityReview.md,PerformanceReview.md,ReleaseReadiness.md}

touch docs/17-showcase/{FeatureMatrix.md,SkillsDemonstrated.md,ArchitectureHighlights.md,InterviewTalkingPoints.md,ResumeHighlights.md}

touch docs/templates/{ADR_Template.md,Requirement_Template.md,UserStory_Template.md,Review_Template.md,Runbook_Template.md,ReleaseNotes_Template.md}

mkdir -p auth-service engagement-service docker scripts

echo "Documentation structure created successfully."