#!/usr/bin/env perl

use strict;
use warnings;
use English;

use 5.026;

my %templ_result_to_srcs;
my $currentresultref;

open(my $fh, '<:encoding(UTF-8)', "making/templating.dat")
    or die("Couldn't open making/templating.dat $!");
while (my $row = <$fh>) {
    chomp $row;
    @tokens = split /\s/, $row;
    foreach my $token (@tokens) {
        unless (ord $token == ord ";") {
            unless $currentresultref {
                $currentresultref = [];
                $templ_result_to_srcs{$token} = $currentresultref;
            } else {
                push @$currentresultref, $token;
            }
        } else {
            undef $currentresultref;
        }
    }
    undef @tokens;
}
close($fh);
undef $fh;
undef $currentresultref;
my %templ_src_to_results;
while ((my $resultname, my $currentresultref) = each (%templ_result_to_srcs)) {
    foreach my $srcname (@$currentresultref) {
        unless exists($templ_src_to_results{$srcname}) {
            $templ_src_to_results{$srcname} = [];
        }
        push @$templ_src_to_results{"templ/" . $srcname}, $resultname;
    }
}
undef $currentresultref;
open(my $fh, '<:encoding(UTF-8)', "making/statics.dat")
    or die("Couldn't open making/statics.dat $!");
my $curdir = '';
my %statics_src_to_result;
while (my $row = <$fh>) {
    chomp $row;
    @tokens = split /\s/, $row;
    foreach my $token (@tokens) {
        unless (ord $token == ord ";") {
            unless $curdir {
                $curdir = $token;
            } else {
                $src_to_results{"static/" . $curdir . $token} = $token;
            }
        } else {
            $curdir = '';
        }
    }
    undef @tokens;
}
close($fh);
undef $fh;
undef $curdir;