package br.com.akross.akrossapi.mock;

import br.com.akross.akrossapi.controllers.request.CreateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.request.CreateCompanyRequest;
import br.com.akross.akrossapi.controllers.request.PhotoRequest;
import br.com.akross.akrossapi.models.Address;
import br.com.akross.akrossapi.models.Collaborator;
import br.com.akross.akrossapi.models.Company;
import br.com.akross.akrossapi.models.Photo;
import br.com.akross.akrossapi.models.Squad;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

public class MockUtils {


  public static Company getCompany() {
    Company company = new Company();
    company.setId(UUID.fromString("36837a3f-e23f-4da4-9673-2b79f6963126"));
    company.setName("Company");
    company.setCnpj("12345678901234");
    company.setAddress(getAddress());
    company.setPhoto(getPhoto());
    company.setSquads(List.of(getSquad()));
//    company.setCollaborators(List.of(getCollaborator()));
    company.setActive(true);
    return company;
  }
  public static CreateCompanyRequest getCompanyRequest() {

    return new CreateCompanyRequest(
      "Company",
      "12345678901234",
      new PhotoRequest(getBase64Image(), "image/png"),
      getAddress()
    );
  }

  public static Address getAddress() {
    Address address = new Address();
    address.setStreetAddress("Street");
    address.setNumber(123);
    address.setComplement("Complement");
    address.setCity("City");
    address.setState("State");
    address.setPostalCode("12345678");
    return address;
  }

  public static Photo getPhoto() {
    Photo photo = new Photo();
    photo.setPhotoFilePath("https://www.google.com.br");
    photo.setPhotoContentType("image/png");
    return photo;
  }

  public static Squad getSquad() {
    Squad squad = new Squad();
    squad.setId(UUID.fromString("f9861ea0-7432-4a88-b05f-5ed7315c93b8"));
    squad.setName("Squad");
    squad.setActive(true);
    return squad;
  }

  public static Collaborator getCollaborator() {
    Collaborator collaborator = new Collaborator();
    collaborator.setId(UUID.fromString("1c226a82-f073-4859-b356-2f0bdaf35a51"));
    collaborator.setName("Collaborator");
    collaborator.setActive(true);
    collaborator.setPhone("21999999999");
    collaborator.setCpf("12345678901");
    collaborator.setEmail("collabotaror@email.com");
    collaborator.setPhoto(getPhoto());
    return collaborator;
  }

  public static CreateCollaboratorRequest getCollaboratorRequest() {
    return new CreateCollaboratorRequest(
      "Collaborator",
      "colaborator@email.com",
      "21999999999",
      "12345678901",
      new PhotoRequest(getBase64Image(), "image/png"),
      List.of(getSquad().getId())
    );
  }

  @SneakyThrows
  public static String getBase64Image() {
    FileInputStream fileInputStream = new FileInputStream(
      "src/test/java/resources/base64-image.txt"
    );
    return IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
  }
}
