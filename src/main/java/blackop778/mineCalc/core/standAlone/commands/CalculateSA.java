package blackop778.mineCalc.core.standAlone.commands;

import blackop778.mineCalc.core.CalcExceptions;
import blackop778.mineCalc.core.CalcExceptions.*;
import blackop778.mineCalc.core.Calculator;
import blackop778.mineCalc.core.standAlone.ICommandSA;
import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculateSA implements ICommandSA {

    @Nonnull
    @Override
    public String getUsage() {
        return "calc <boolean: use OOPS> [number][operator][number]<operator><number>";
    }

    @Nonnull
    @Override
    public String getTrigger() {
        return "calc";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
    }

    @Override
    public String execute(@Nonnull String[] args) {
        String print;
        boolean useOOPS;

        if (args.length == 0)
            return "Usage: " + getUsage();

        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
            useOOPS = Boolean.valueOf(args[0]);
            args[0] = "";
        }
        else
        {
            useOOPS = true;
        }

        StringBuilder condensedMath = new StringBuilder();
        for (String s : args) {
            condensedMath.append(s);
        }

        try {
            double answer;
            answer = Calculator.evaluate(condensedMath.toString(), useOOPS, Calculator.consoleLastOutput,
                    FancyRemainders.getFancyRemainder());
            Calculator.consoleLastOutput = answer;
            if (answer % 1 == 0 && answer < Integer.MAX_VALUE && answer > Integer.MIN_VALUE) {
                int i = (int) answer;
                print = String.valueOf(i);
            } else {
                print = String.valueOf(answer);
            }
        } catch (ImaginaryNumberException e) {
            return "Error: Imaginary numbers are not supported";
        } catch (DivisionException er) {
            return "Error: Cannot divide by 0";
        } catch (OperatorException err) {
            return "Error: Valid symbols are '+, -, *, /, %, ^, /--'";
        } catch (PreviousOutputException erro) {
            return "Error: There is no previous output to insert";
        } catch (CustomFunctionException error) {
            return error.getMessage();
        } catch (FancyRemainderException errors) {
            int num1 = (int) (errors.numerator / errors.denominator);
            double num2 = errors.numerator % errors.denominator;
            print = num1 + "R";
            if (num2 % 1 == 0) {
                int i = (int) num2;
                print += String.valueOf(i);
            } else {
                print += String.valueOf(num2);
            }
            Calculator.consoleLastOutput = num2;
        } catch (AllStandinsUsedException errorsA) {
            return "Error: Please exclude one of the following symbols:" + errorsA.getMessage();
        } catch (MultiplePointsException errorsAr) {
            return "Error: A number may only have one decimal point";
        } catch (UsageException errorsAre) {
            return "Usage: " + getUsage();
        } catch (InvalidNumberException errorsAreF) {
            return "Error: The following String could not be interpreted as a number: " + errorsAreF.getMessage();
        } catch (ParenthesisException errorsAreFu) {
            if (errorsAreFu.tooMany)
                return "Error: Too many opening parenthesis have been input. Some may not have been closed";
            else
                return "Error: Too many closing parenthesis have been input. Some may not have been opened";
        } catch (CalcExceptions errorsAreFun) {
            errorsAreFun.printStackTrace();
            return "Error: An unknown error occurred" + (errorsAreFun.getLocalizedMessage() == null ? ""
                    : ". Message: " + errorsAreFun.getLocalizedMessage());
        }

        // Prepend the arguments to the output, if configured to
        if (ReturnInput.getReturnInput()) {
            print = condensedMath + "=" + print;
        }

        return print;
    }

    @Nonnull
    @Override
    public String getEffect() {
        return "Calculates the value of all the arguments";
    }

}
