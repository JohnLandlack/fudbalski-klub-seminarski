package rs.fon.server.forme;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import rs.fon.server.Server;

/**
 * Grafički interfejs za pokretanje i zaustavljanje servera. Raspored veran
 * starom projektu (isti GroupLayout: tabela, dugmad, status).
 * @author Jovan Radojičić
 */
public class ServerskaForma extends javax.swing.JFrame {

    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JTable tblUlogovani = new JTable();
    private final JButton btnPokreni = new JButton("Pokreni server");
    private final JButton btnZaustavi = new JButton("Zaustavi server");
    private final JLabel lblStatus = new JLabel("Status:");

    private Server server;

    public ServerskaForma() {
        setTitle("Serverska forma");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tblUlogovani.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Ulogovani treneri"}
        ));
        jScrollPane1.setViewportView(tblUlogovani);

        btnPokreni.setEnabled(true);
        btnZaustavi.setEnabled(false);
        btnPokreni.addActionListener(e -> pokreniServer());
        btnZaustavi.addActionListener(e -> zaustaviServer());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnPokreni, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnZaustavi, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(lblStatus)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPokreni)
                    .addComponent(btnZaustavi))
                .addGap(41, 41, 41))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void pokreniServer() {
        server = new Server();
        server.start();
        lblStatus.setText("<html>Status: <b>POKRENUT</b></html>");
        btnPokreni.setEnabled(false);
        btnZaustavi.setEnabled(true);
    }

    private void zaustaviServer() {
        server.zaustaviServer();
        lblStatus.setText("<html>Status: <b>ZAUSTAVLJEN</b></html>");
        btnPokreni.setEnabled(true);
        btnZaustavi.setEnabled(false);
    }
}
