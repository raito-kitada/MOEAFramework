/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.IO;

import aos.history.CreditHistory;
import aos.creditassigment.Credit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.moeaframework.core.Variation;

/**
 * This class is responsible for saving the history of credits received by
 * operators and other statistics regarding operators credit history.
 *
 * @author nozomihitomi
 */
public class IOCreditHistory {

    /**
     * Saves the credit history at the specified filename. The file will be a a
     * dlm file with n rows to represent the n iterations. Each column will have
     * the credits received in the ith iteration by the mth operator. If no
     * credit was received a -1 will be stored to differentiate it from a 0
     * credit
     *
     * @param creditHistory The quality history to save
     * @param filename filename including the path and the extension.
     * @param separator the type of separator desired
     * @return true if the save is successful
     */
    public static boolean saveHistory(CreditHistory creditHistory, String filename, String separator) {
        Collection<Variation> operators = creditHistory.getOperators();
        try (FileWriter fw = new FileWriter(new File(filename))) {
            for(Variation oper:operators){
                Collection<Credit> hist = creditHistory.getHistory(oper);
                if(hist.isEmpty())
                    continue;
                int[] iters = new int[hist.size()];
                double[] vals = new double[hist.size()];
                Iterator<Credit> iter = hist.iterator();
                Credit reward = iter.next();
                iters[0]=reward.getIteration();
                vals[0]=reward.getValue1();
                int index=0;
                while(iter.hasNext()){
                    Credit nextReward = iter.next();
                    int iteration = nextReward.getIteration();
                    double rewardVal = nextReward.getValue1();
                    iters[index] = iteration;
                    vals[index] = rewardVal;
                    index++;
                }

                fw.append("iteration" + separator);
                for(int i=0;i<index;i++){
                    fw.append(Integer.toString(iters[i]) + separator);
                }
                fw.append(Integer.toString(iters[index]) + "\n");
                
                String[] operatorName = oper.toString().split("operator.");
                String[] splitName = operatorName[operatorName.length - 1].split("@");
                fw.append(splitName[0] + separator);
                for(int i=0;i<index;i++){
                    fw.append(Double.toString(vals[i]) + separator);
                }
                fw.append(Double.toString(vals[index]) + "\n");
                
            }
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(IOQualityHistory.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Saves the credit history at the specified filename as a java Object. The
     * file an instance of CreditHistory
     *
     * @param creditHistory The quality history to save
     * @param filename filename including the path and the extension.
     */
    public static void saveHistory(CreditHistory creditHistory, String filename) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));) {
            os.writeObject(creditHistory);
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(IOCreditHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads the CreditHistory instance saved by using saveHistory() from the
     * filename.
     *
     * @param filename the file name (path and extension included)
     * @return the CreditHistory instance saved by using saveHistory()
     */
    public static CreditHistory loadHistory(String filename) {
        CreditHistory hist = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename))) {
            hist = (CreditHistory) is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(IOCreditHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IOCreditHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hist;
    }
}