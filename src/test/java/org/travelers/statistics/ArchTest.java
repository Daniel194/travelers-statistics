package org.travelers.statistics;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.travelers.statistics");

        noClasses()
            .that()
                .resideInAnyPackage("org.travelers.statistics.service..")
            .or()
                .resideInAnyPackage("org.travelers.statistics.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.travelers.statistics.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
