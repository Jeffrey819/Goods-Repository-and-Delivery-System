package com.example.project.security;

import com.example.project.security.rsa.RSAManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component
public class RSADecryptionFilter extends OncePerRequestFilter {

    @Autowired
    private RSAManager rsaManager;

    private final PrivateKey privateKey = rsaManager.getRsaKeyPair().getPrivateKey();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Only process if the request has a body
        if (request.getContentLength() > 0 || request.getInputStream().available() > 0) {
            try {
                // Read the encrypted data from the request body
                String encryptedData = getRequestBody(request);

                // Decrypt the data using the private key
                String decryptedData = decrypt(encryptedData, privateKey);

                // Wrap the decrypted data into a new HttpServletRequest
                HttpServletRequest decryptedRequest = new DecryptedHttpServletRequest(request, decryptedData);

                // Proceed with the filter chain
                filterChain.doFilter(decryptedRequest, response);
            } catch (Exception e) {
                // Handle decryption errors
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid encrypted data");
            }
        } else {
            // If there's no body, proceed as usual
            filterChain.doFilter(request, response);
        }
    }

    // Utility method to read request body as a string
    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try (InputStream inputStream = request.getInputStream()) {
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw new IOException("Error reading the request body", ex);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    // Decrypts the encrypted data using RSA private key
    private String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Custom HttpServletRequestWrapper to provide decrypted data
    private static class DecryptedHttpServletRequest extends HttpServletRequestWrapper {

        private final byte[] decryptedBody;

        public DecryptedHttpServletRequest(HttpServletRequest request, String decryptedData) {
            super(request);
            this.decryptedBody = decryptedData.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedBody);

            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    // Not implementing async read listener
                }

                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
        }

        @Override
        public int getContentLength() {
            return decryptedBody.length;
        }

        @Override
        public long getContentLengthLong() {
            return decryptedBody.length;
        }
    }
}