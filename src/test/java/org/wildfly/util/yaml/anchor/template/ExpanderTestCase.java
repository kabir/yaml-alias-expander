package org.wildfly.util.yaml.anchor.template;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
public class ExpanderTestCase {

    @Test
    public void testAliasesAreExpanded() throws Exception {
        File inputFile = new File(this.getClass().getResource("simple.yml").toURI());
        File expectedFile = new File(this.getClass().getResource("simple-expanded.yml").toURI());

        Expander expander = new Expander(inputFile);
        String expanded = expander.expandInput();

        Yaml yaml = new Yaml();
        Object expandedObject = yaml.load(expanded);
        Object expectedExpanded = yaml.load(new FileReader(expectedFile));

        Assert.assertEquals(expectedExpanded, expandedObject);
    }

    @Test
    public void testCommandLine() throws Exception {
        File inputFile = new File(this.getClass().getResource("simple.yml").toURI());
        File expectedFile = new File(this.getClass().getResource("simple-expanded.yml").toURI());

        File outputDir = new File("target/test/output");
        if (!Files.exists(outputDir.toPath())) {
            Files.createDirectories(outputDir.toPath());
        }
        File outputFile = new File(outputDir, "expanded.yml");
        if (Files.exists(outputFile.toPath())) {
            Files.delete(outputFile.toPath());
        }

        String[] args = new String[2];
        args[0] = inputFile.getAbsolutePath();
        args[1] = outputFile.getAbsolutePath();
        Expander.main(args);

        Yaml yaml = new Yaml();
        Object expandedObject = yaml.load(new FileReader(outputFile));
        Object expectedExpanded = yaml.load(new FileReader(expectedFile));
        Assert.assertEquals(expectedExpanded, expandedObject);
    }
}
