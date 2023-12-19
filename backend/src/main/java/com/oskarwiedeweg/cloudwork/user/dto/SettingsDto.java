package com.oskarwiedeweg.cloudwork.user.dto;

import com.oskarwiedeweg.cloudwork.BitUtils;
import lombok.Data;

import java.util.Set;

@Data
public class SettingsDto {

    private final Set<BitUtils.SettingBits> activeSettings;

}
