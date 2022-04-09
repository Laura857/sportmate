package com.example.sportmate.record.external;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record SendinblueSendEmailRequest(
        @NotEmpty(message = "La liste de destinairaires est obligatoire")
        List<ToDetails> to,

        int templateId) {
}
