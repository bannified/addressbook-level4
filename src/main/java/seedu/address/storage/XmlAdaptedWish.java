package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Phone;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.Wish;

/**
 * JAXB-friendly version of the Wish.
 */
public class XmlAdaptedWish {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wish's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String remark;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWish() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given wish details.
     */
    public XmlAdaptedWish(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = "";
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedWish
     */
    public XmlAdaptedWish(Wish source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getUrl().value;
        remark = source.getRemark().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted wish object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wish
     */
    public Wish toModelType() throws IllegalValueException {
        final List<Tag> wishTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            wishTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(address)) {
            throw new IllegalValueException(Url.MESSAGE_URL_CONSTRAINTS);
        }
        final Url modelUrl = new Url(address);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(this.remark);

        final Set<Tag> modelTags = new HashSet<>(wishTags);
        return new Wish(modelName, modelPhone, modelEmail, modelUrl, modelRemark, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedWish)) {
            return false;
        }

        XmlAdaptedWish otherWish = (XmlAdaptedWish) other;
        return Objects.equals(name, otherWish.name)
                && Objects.equals(phone, otherWish.phone)
                && Objects.equals(email, otherWish.email)
                && Objects.equals(address, otherWish.address)
                && Objects.equals(remark, otherWish.remark)
                && tagged.equals(otherWish.tagged);
    }
}