package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Reimbursement {

    // MEMBER VARIABLES
    private Integer reimbursementId;
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp dateSubmitted; // LocalDateTime is the correspondent of postgresql timestamp.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp dateResolved;
    private String description;
    private byte[] receipt;
    private Integer authorId;
    private String authorFirstName;
    private String authorLastName;
    private Integer resolverId;
    private String resolverFirstName;
    private String resolverLastName;
    private Integer statusId;
    private String status;
    private Integer typeId;
    private String type;

    // CONSTRUCTORS

    // use by jackson library to create the user object form the body using ctx.bodyAsClass();
    public Reimbursement() {
    }

    public Reimbursement(Integer reimbursementId, Double amount, Timestamp dateSubmitted, Timestamp dateResolved, String description,
                         byte[] receipt, Integer authorId, String authorFirstName, String authorLastName, Integer resolverId,
                         String resolverFirstName, String resolverLastName, Integer statusId, String status, Integer typeId,
                         String type) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.dateResolved = dateResolved;
        this.description = description;
        this.receipt = receipt;
        this.authorId = authorId;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.resolverId = resolverId;
        this.resolverFirstName = resolverFirstName;
        this.resolverLastName = resolverLastName;
        this.statusId = statusId;
        this.status = status;
        this.typeId = typeId;
        this.type = type;
    }

    // constructor to use with the insert sql(has id for testing the update method which needs it)
    // used to create a reimbursement. For that operation, information about resolver are not available yet.
    // they will be provided at updated (approve or denied)
    public Reimbursement(Integer reimbursementId, Double amount, Timestamp dateSubmitted, String description, Integer authorId,
                         Integer statusId, Integer typeId) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.description = description;
        this.authorId = authorId;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    // constructor to use with the insert sql
        // used to create a reimbursement. For that operation, information about resolver are not available yet.
       // they will be provided at updated (approve or denied)
    public Reimbursement(Double amount, Timestamp dateSubmitted, String description, Integer authorId,
                         Integer statusId, Integer typeId) {
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.description = description;
        this.authorId = authorId;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    // GETTERS AND SETTERS

    public Integer getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Timestamp getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(Timestamp dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public Integer getResolverId() {
        return resolverId;
    }

    public void setResolverId(Integer resolverId) {
        this.resolverId = resolverId;
    }

    public String getResolverFirstName() {
        return resolverFirstName;
    }

    public void setResolverFirstName(String resolverFirstName) {
        this.resolverFirstName = resolverFirstName;
    }

    public String getResolverLastName() {
        return resolverLastName;
    }

    public void setResolverLastName(String resolverLastName) {
        this.resolverLastName = resolverLastName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // METHODS OVERRIDDEN

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", amount=" + amount +
                ", dateSubmitted=" + dateSubmitted +
                ", dateResolved=" + dateResolved +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", authorId=" + authorId +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", resolverId=" + resolverId +
                ", resolverFirstName='" + resolverFirstName + '\'' +
                ", resolverLastName='" + resolverLastName + '\'' +
                ", statusId=" + statusId +
                ", status='" + status + '\'' +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                '}';
    }
}
