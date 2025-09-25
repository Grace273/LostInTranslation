package translation;

import javax.swing.*;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //dropdown
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            Translator translator = new JSONTranslator();
            LanguageCodeConverter langConverter = new LanguageCodeConverter();

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(langConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String languageSelection = languageComboBox.getSelectedItem().toString();
                        JOptionPane.showMessageDialog(null, "user selected " + languageSelection + "!");
                    }
                }


            });

            // country menu
            JPanel countryPanel = new JPanel();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            String[] countries = new String[countryConverter.getNumCountries()];
            int i = 0;
            for (String countryCode: translator.getCountryCodes()) {
                countries[i] = countryConverter.fromCountryCode(countryCode);
                i++;
            }

            JList<String> countryList = new JList<>(countries);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryPanel.add(countryScrollPane, 0);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
