package org.wildfly.util.yaml.anchor.template;

import java.io.File;
import java.io.FileReader;

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

        Expander expander = new Expander(inputFile.getAbsolutePath());
        String expanded = expander.expandInput();

        Yaml yaml = new Yaml();
        Object expandedObject = yaml.load(expanded);
        Object expectedExpanded = yaml.load(new FileReader(expectedFile));

        Assert.assertEquals(expectedExpanded, expandedObject);
    }
}
