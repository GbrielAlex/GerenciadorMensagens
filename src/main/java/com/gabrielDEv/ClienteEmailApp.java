package com.gabrielDEv;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ClienteEmailApp extends JFrame {

    // Classe representando o Cliente
    static class Cliente {
        double id;
        String nome;
        String cpf;
        List<Email> emails;
    }

    // Classe representando o Email
    static class Email {
        double id;
        String titulo;
        String emailCliente;
        String corpoEmail;
    }

    private JList<String> clienteList;
    private List<Cliente> clientes;

    public ClienteEmailApp(List<Cliente> clientes) {
        this.clientes = clientes;

        setTitle("Lista de Clientes");
        setSize(960, 540); // Tamanho fixo da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false); // Janela não redimensionável

        // Lista de nomes e CPFs dos clientes
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Cliente cliente : clientes) {
            listModel.addElement(cliente.nome + " - " + cliente.cpf); // Exibe nome e CPF
        }

        clienteList = new JList<>(listModel);
        clienteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clienteList.setCellRenderer(new CenteredListCellRenderer()); // Centraliza o texto

        clienteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Clique duplo
                    int index = clienteList.locationToIndex(e.getPoint());
                    Cliente cliente = clientes.get(index);
                    mostrarEmails(cliente);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(clienteList);
        add(scrollPane);

        setVisible(true);
    }

    // Exibe os emails em uma nova janela
    private void mostrarEmails(Cliente cliente) {
        JFrame emailFrame = new JFrame("Emails de " + cliente.nome);
        emailFrame.setSize(400, 300);
        emailFrame.setLocationRelativeTo(null);

        JTextArea emailTextArea = new JTextArea();
        emailTextArea.setEditable(false);

        if (cliente.emails.isEmpty()) {
            emailTextArea.setText("Este cliente não enviou nenhum email.");
        } else {
            StringBuilder emailContent = new StringBuilder();
            for (Email email : cliente.emails) {
                emailContent.append("Título: ").append(email.titulo).append("\n");
                emailContent.append("Email: ").append(email.emailCliente).append("\n");
                emailContent.append("Corpo: ").append(email.corpoEmail).append("\n\n");
            }
            emailTextArea.setText(emailContent.toString());
        }

        emailFrame.add(new JScrollPane(emailTextArea));
        emailFrame.setVisible(true);
    }

    // Renderer para centralizar o texto no JList
    static class CenteredListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto
            return label;
        }
    }

    // Método para buscar os clientes da URL
    public static List<Cliente> fetchClientesFromURL() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/clientes"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonClientes = response.body();

            Gson gson = new Gson();
            java.lang.reflect.Type clienteListType = new TypeToken<List<Cliente>>(){}.getType();

            return gson.fromJson(jsonClientes, clienteListType);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // Busca os dados da URL e inicializa a interface gráfica
        SwingUtilities.invokeLater(() -> {
            List<Cliente> clientes = fetchClientesFromURL();
            if (clientes != null) {
                new ClienteEmailApp(clientes);
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao buscar dados dos clientes.");
            }
        });
    }
}
