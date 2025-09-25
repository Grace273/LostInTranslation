package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel();
            //dropdown
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"), 0);
            Translator translator = new JSONTranslator();
            LanguageCodeConverter langConverter = new LanguageCodeConverter();
            CountryCodeConverter countryConverter = new CountryCodeConverter();

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(langConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);



            // country menu
            JPanel countryPanel = new JPanel();
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

            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedCountry = countryList.getSelectedValue();
                        String languageSelection = languageComboBox.getSelectedItem().toString();
                        String languageCode = langConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                        String countryCode = countryConverter.fromCountry(selectedCountry);
                        resultLabel.setText("\t\t\t\t\t\t\t" + translator.translate(countryCode, languageCode));
                    }
                }


            });

            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String selectedCountry = countryList.getSelectedValue();
                    String languageCode = langConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String countryCode = countryConverter.fromCountry(selectedCountry);
                    System.out.println(languageCode + " " + countryCode + " " + selectedCountry);
                    resultLabel.setText("\t\t\t\t\t\t\t" + translator.translate(countryCode, languageCode));
                }
            });




            buttonPanel.add(resultLabel);


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
