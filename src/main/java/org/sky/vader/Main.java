package org.sky.vader;

/**
 * @author maksim
 * @since 04.11.2019
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            var name = executableName();
            System.out.println("Usage:\n\t" + name + " <expression_result> <complexity>" +
                    "\n\t\texpression_result — the given result of calculating the expression" +
                    "\n\t\tcomplexity — is complexity of the generated expression, measure in conventional units" +
                    "\nExample:" +
                    "\n$ " + name + " 42 1" +
                    "\n252 / 6");
            System.exit(0);
        }

        try {
            tryGenerateExpression(args);
        } catch (Exception e) {
            System.out.println("Something wrong: " + e.getMessage());
            System.err.println(e);
            System.exit(-1);
        }
    }

    // https://stackoverflow.com/questions/11158235/get-name-of-executable-jar-from-within-main-method
    private static String executableName() {
        return "java -jar " + new java.io.File(Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();
    }

    private static void tryGenerateExpression(String[] args) {
        int result = Integer.parseInt(args[0]);
        int complexity = Integer.parseInt(args[1]);

        var generator = new ExpressionGenerator(result, complexity);
        System.out.println(generator.expression());
    }
}
